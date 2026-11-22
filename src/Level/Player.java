package Level;

import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Mouse;
import Engine.GraphicsHandler;
import GameObject.GameObject;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Utils.Direction;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected int interactionRange = 1;
    protected Direction currentWalkingXDirection;
    protected Direction currentWalkingYDirection;
    protected Direction lastWalkingXDirection;
    protected Direction lastWalkingYDirection;

    // values used to handle player movement
    protected float moveAmountX, moveAmountY;
    protected float lastAmountMovedX, lastAmountMovedY;

    // values used to keep track of player's current state
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected Direction facingDirection;
    protected Direction lastMovementDirection;

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
    protected Key MOVE_LEFT_KEY = Key.A;
    protected Key LIGHTNING_STRIKE_KEY = Key.E;
    protected Key WALL_OF_FIRE_KEY = Key.Q;
    protected Key MOVE_RIGHT_KEY = Key.D;
    protected Key MOVE_UP_KEY = Key.W;
    protected Key MOVE_DOWN_KEY = Key.S;
    protected Key INTERACT_KEY = Key.SPACE;
    protected Key ROLL_KEY = Key.R;

    protected boolean isLocked = false;
    protected boolean leftClickLocked = false;
    private static final long ROLL_INVULNERABILITY_DURATION_MS = 5000;
    private static final long ROLL_COOLDOWN_MS = 10000;
    private long rollInvulnerableUntil;
    private long nextRollAvailableAt;
    
    protected int attackCooldownCounter = 0;
    protected static final int cooldownTime = 30;
    protected static final int abilityCooldownTime = 60;
    protected int lightningDamage = 50;
    protected int wallOfFireDamage = 50;
    protected int attackDamage = 25;
    
    protected static final int LIGHTNING_RADIUS = 100;

    protected static final int WALL_OF_FIRE_RANGE = 200;
    private static final int WALL_OF_FIRE_WIDTH = 20;
    private static final int WALL_OF_FIRE_DURATION = 90;
    private static final int WALL_OF_FIRE_DAMAGE_INTERVAL = 15;
    private static final int LIGHTNING_EFFECT_DURATION = 18;
    protected static final int HIT_RANGE = 130;
    private static final int MAX_HEALTH = 100;

    protected int karma = 0;
    protected boolean isAttacking = false;
protected boolean damageDealtThisAttack = false;
    private boolean lightningStrikeUnlocked;
    private boolean wallOfFireUnlocked;
    private int healingPotionCount;
    private boolean potionKeyWasDown;
    private final boolean[] potionRewardGranted = new boolean[8];
    private boolean isAimingLightning;
    private boolean lightningStrikeActive;
    private double lightningAimX;
    private double lightningAimY;
    private double lightningStrikeX;
    private double lightningStrikeY;
    private int lightningFramesRemaining;
    private int lightningCooldownCounter;
    private boolean isAimingWallOfFire;
    private boolean wallOfFireActive;
    private double wallOfFireAimStartX;
    private double wallOfFireAimStartY;
    private double wallOfFireAimEndX;
    private double wallOfFireAimEndY;
    private double wallOfFireStartX;
    private double wallOfFireStartY;
    private double wallOfFireEndX;
    private double wallOfFireEndY;
    private int wallOfFireFramesRemaining;
    private int wallOfFireCooldownCounter;
    private final HashMap<NPC, Integer> wallOfFireDamageCooldowns = new HashMap<>();

    public void addKarma(int amount){
        karma += amount;
    }

    public void subtractKarma(int amount){
        karma -= amount;
    }

    public int getKarma(){
        return karma;
    }

    public void enterFloor(int floorNumber) {
        if (floorNumber < 1 || floorNumber >= potionRewardGranted.length) {
            throw new IllegalArgumentException("Invalid floor number: " + floorNumber);
        }

        if (floorNumber >= 3) {
            lightningStrikeUnlocked = true;
        }
        if (floorNumber >= 6) {
            wallOfFireUnlocked = true;
        }
        if ((floorNumber == 2 || floorNumber == 4 || floorNumber == 6)
                && !potionRewardGranted[floorNumber]) {
            healingPotionCount++;
            potionRewardGranted[floorNumber] = true;
        }
    }

    protected int health = MAX_HEALTH;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        playerState = PlayerState.STATIC;
        previousPlayerState = playerState;
        this.affectedByTriggers = true;
    }

    public void update() {
        if (!isLocked) {
            updateRoll();
            moveAmountX = 0;
            moveAmountY = 0;

            // if player is currently playing through level (has not won or lost)
            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
            } while (previousPlayerState != playerState);

            // move player with respect to map collisions based on how much player needs to move this frame
            lastAmountMovedY = super.moveYHandleCollision(moveAmountY);
            lastAmountMovedX = super.moveXHandleCollision(moveAmountX);
        }

    }
    public void updateFacingAndAnimation() {
    faceMouse();

    handleMouseAttack();
    handlePlayerAbility();

    handlePlayerAnimation();

    updateLockedKeys();

    super.update();

    if (isAttacking) {
        if (!damageDealtThisAttack && getCurrentFrameIndex() == 2) {
            performAttack();
            damageDealtThisAttack = true;
        }

        if (hasAnimationLooped) {
            isAttacking = false;
            damageDealtThisAttack = false;
        }
    }
}

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STATIC:
                playerStanding();
                break;
            case MOVE:
                playerWalking();
                break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            keyLocker.lockKey(INTERACT_KEY);
            map.entityInteract(this);
        }

        // if a walk key is pressed, player enters WALKING state
        if ((Keyboard.isKeyDown(MOVE_LEFT_KEY) != Keyboard.isKeyDown(MOVE_RIGHT_KEY)) || (Keyboard.isKeyDown(MOVE_UP_KEY) != Keyboard.isKeyDown(MOVE_DOWN_KEY))) {
            playerState = PlayerState.MOVE;
        }
        /*
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(MOVE_UP_KEY) || Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            playerState = PlayerState.MOVE;
        }
        */
    }

    // player WALKING state logic
    protected void playerWalking() {
        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            keyLocker.lockKey(INTERACT_KEY);
            map.entityInteract(this);
        }
        
        // if walk up key is pressed *but not down*, move player up
        if (Keyboard.isKeyDown(MOVE_UP_KEY) && !Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            moveAmountY -= walkSpeed;
            //facingDirection = Direction.UP;
            currentWalkingYDirection = Direction.UP;
            lastWalkingYDirection = Direction.UP;
        }
        // if walk down key is pressed *but not up*, move player down
        else if (Keyboard.isKeyDown(MOVE_DOWN_KEY) && !Keyboard.isKeyDown(MOVE_UP_KEY)) {
            moveAmountY += walkSpeed;
            //facingDirection = Direction.DOWN;
            currentWalkingYDirection = Direction.DOWN;
            lastWalkingYDirection = Direction.DOWN;
        }
        else {
            currentWalkingYDirection = Direction.NONE;
        }

        // if walk left key is pressed *but not right*, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) && !Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            moveAmountX -= walkSpeed;
            //facingDirection = Direction.LEFT;
            currentWalkingXDirection = Direction.LEFT;
            lastWalkingXDirection = Direction.LEFT;
        }
        // if walk right key is pressed *but not left*, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY) && !Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
            moveAmountX += walkSpeed;
            //facingDirection = Direction.RIGHT;
            currentWalkingXDirection = Direction.RIGHT;
            lastWalkingXDirection = Direction.RIGHT;
        }
        else {
            currentWalkingXDirection = Direction.NONE;
        }

        if ((currentWalkingXDirection == Direction.RIGHT || currentWalkingXDirection == Direction.LEFT) && currentWalkingYDirection == Direction.NONE) {
            lastWalkingYDirection = Direction.NONE;
        }

        if ((currentWalkingYDirection == Direction.UP || currentWalkingYDirection == Direction.DOWN) && currentWalkingXDirection == Direction.NONE) {
            lastWalkingXDirection = Direction.NONE;
        }

        if ((Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY) && Keyboard.isKeyUp(MOVE_UP_KEY) && Keyboard.isKeyUp(MOVE_DOWN_KEY))
        || Keyboard.isKeyDown(MOVE_LEFT_KEY) && Keyboard.isKeyDown(MOVE_RIGHT_KEY) && Keyboard.isKeyDown(MOVE_UP_KEY) && Keyboard.isKeyDown(MOVE_DOWN_KEY)
        || (Keyboard.isKeyDown(MOVE_LEFT_KEY) && Keyboard.isKeyDown(MOVE_RIGHT_KEY)) && (Keyboard.isKeyDown(MOVE_UP_KEY) == Keyboard.isKeyDown(MOVE_DOWN_KEY))
        || (Keyboard.isKeyDown(MOVE_LEFT_KEY) == Keyboard.isKeyDown(MOVE_RIGHT_KEY)) && (Keyboard.isKeyDown(MOVE_UP_KEY) && Keyboard.isKeyDown(MOVE_DOWN_KEY))
        ) {
            playerState = PlayerState.STATIC;
        }

        // For some reason, doing *this* breaks the game by teleporting the player outside the map
        // I'd like to implement something like this later when I get a chance to handle the player animation
        /* 
        if ((Keyboard.isKeyDown(MOVE_LEFT_KEY) == Keyboard.isKeyDown(MOVE_RIGHT_KEY)) && (Keyboard.isKeyDown(MOVE_UP_KEY) == Keyboard.isKeyDown(MOVE_DOWN_KEY))) {
            playerState = PlayerState.STATIC;
        }
        */
    }
    /*protected void faceMouse() {
        int offsetX = getMouseOffsetX();
        int offsetY = getMouseOffsetY();

        int absOffsetX = Math.abs(offsetX);
        int absOffsetY = Math.abs(offsetY);

        if (absOffsetX > absOffsetY) {
            if (offsetX > 0) {
                facingDirection = Direction.RIGHT;
            } else {
                facingDirection = Direction.LEFT;
            }
        } else {
            if (offsetY > 0) {
                facingDirection = Direction.DOWN;
            } else {
                facingDirection = Direction.UP;
            }
         }
     }*/
    protected void faceMouse() {

    int offsetX = getMouseOffsetX();

    if (offsetX > 0) {
        facingDirection = Direction.RIGHT;
    }
    else if (offsetX < 0) {
        facingDirection = Direction.LEFT;
    }
}
    

    protected int getMouseOffsetX(){
    int mouseX = Mouse.getMouseX();
    int playerScreenX = Math.round(getCalibratedXLocation());
    return mouseX - playerScreenX;
    }
    protected int getMouseOffsetY(){
        int mouseY = Mouse.getMouseY();
        int playerScreenY = Math.round(getCalibratedYLocation());
        return mouseY - playerScreenY;
    }
    protected double getAngleToMouse() {
        int offsetX = Mouse.getMouseX();
        int offsetY = Mouse.getMouseY();
        double angleInRadians= Math.atan2(offsetY, offsetX);
        double angleInDegrees = Math.toDegrees(angleInRadians);
        return angleInDegrees;
    }
    /*protected void handleMouseAttack(){
        boolean leftButtonDown = Mouse.isButtonDown(MouseEvent.BUTTON1);

        if (leftButtonDown== true && leftClickLocked == false && attackCooldownCounter <= 0) {
            leftClickLocked = true;
            performAttack();
            attackCooldownCounter = cooldownTime;
        }
        if (leftButtonDown == false) {
            leftClickLocked = false;
        }
        if (attackCooldownCounter > 0) {
            attackCooldownCounter--;
        }
    }*/
   protected void handleMouseAttack() {
    boolean leftButtonDown = Mouse.isButtonDown(MouseEvent.BUTTON1);

    if (leftButtonDown && !leftClickLocked && attackCooldownCounter <= 0 && !isAttacking) {
        leftClickLocked = true;
        isAttacking = true;
        damageDealtThisAttack = false;
        attackCooldownCounter = cooldownTime;

        if (facingDirection == Direction.LEFT) {
            this.currentAnimationName = "ATTACK_LEFT";
        } else {
            this.currentAnimationName = "ATTACK_RIGHT";
        }

        setCurrentAnimationFrameIndex(0);
    }

    if (!leftButtonDown) {
        leftClickLocked = false;
    }

    if (attackCooldownCounter > 0) {
        attackCooldownCounter--;
    }
}

    protected void performAttack() {
        int mouseX = Mouse.getMouseX();
        int mouseY = Mouse.getMouseY();

    for (NPC npc : map.getActiveNPCs()) {
        Rectangle npcBounds = npc.getAttackBounds();

        boolean mouseInsideNpc = mouseX >= npcBounds.getX1() && mouseX <= npcBounds.getX2()
                && mouseY >= npcBounds.getY1() && mouseY <= npcBounds.getY2();

        if (mouseInsideNpc == true) {
            boolean isCloseEnough = isWithinHitRange(npc);
            
            if (isCloseEnough == true) {
                npc.takeDamage(attackDamage);
                System.out.println("Hit! NPC health is now " + npc.getHealth());
            }
            break;
        }
    }
}

    protected boolean isWithinHitRange(NPC npc) {
        Rectangle attackBounds = npc.getAttackBounds();
        float playerX = getCalibratedXLocation();
        float playerY = getCalibratedYLocation();
        float closestX = Math.max(attackBounds.getX1(), Math.min(playerX, attackBounds.getX2()));
        float closestY = Math.max(attackBounds.getY1(), Math.min(playerY, attackBounds.getY2()));

        return Math.abs(playerX - closestX) <= HIT_RANGE
                && Math.abs(playerY - closestY) <= HIT_RANGE;
    }


    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(INTERACT_KEY) && !isLocked) {
            keyLocker.unlockKey(INTERACT_KEY);
        }
        if (Keyboard.isKeyUp(ROLL_KEY)) {
            keyLocker.unlockKey(ROLL_KEY);
        }
    }

    private void updateRoll() {
        if (Keyboard.isKeyDown(ROLL_KEY) && !keyLocker.isKeyLocked(ROLL_KEY)) {
            keyLocker.lockKey(ROLL_KEY);
            long currentTime = System.currentTimeMillis();
            if (currentTime >= nextRollAvailableAt && !isRolling()) {
                rollInvulnerableUntil = currentTime + ROLL_INVULNERABILITY_DURATION_MS;
                nextRollAvailableAt = currentTime + ROLL_COOLDOWN_MS;
            }
        }
    }

    protected boolean isRolling() {
        return System.currentTimeMillis() < rollInvulnerableUntil;
    }

    protected void handlePlayerAbility(){
        if (lightningCooldownCounter > 0) {
            lightningCooldownCounter--;
        }
        if (wallOfFireCooldownCounter > 0) {
            wallOfFireCooldownCounter--;
        }
        updateLightningStrike();
        updateWallOfFire();

        boolean lightningKeyDown = lightningStrikeUnlocked && Keyboard.isKeyDown(LIGHTNING_STRIKE_KEY);
        boolean wallOfFireKeyDown = wallOfFireUnlocked && Keyboard.isKeyDown(WALL_OF_FIRE_KEY);
        boolean interactKeyDown = Keyboard.isKeyDown(INTERACT_KEY);
        boolean potionKeyPressed = interactKeyDown && !potionKeyWasDown;
        potionKeyWasDown = interactKeyDown;

        if (lightningKeyDown && !wallOfFireKeyDown && !interactKeyDown
                && lightningCooldownCounter == 0) {
            isAimingLightning = true;
            updateLightningAim();
        } else if (isAimingLightning && !lightningKeyDown) {
            updateLightningAim();
            isAimingLightning = false;
            castLightningStrike();
        }

        boolean anotherAbilityDown = lightningKeyDown || interactKeyDown;
        if (wallOfFireKeyDown && !anotherAbilityDown && wallOfFireCooldownCounter == 0) {
            isAimingWallOfFire = true;
            updateWallOfFireAim();
        } else if (isAimingWallOfFire && !wallOfFireKeyDown) {
            updateWallOfFireAim();
            isAimingWallOfFire = false;
            if (Math.hypot(wallOfFireAimEndX - wallOfFireAimStartX,
                    wallOfFireAimEndY - wallOfFireAimStartY) > 0) {
                wallOfFireStartX = wallOfFireAimStartX;
                wallOfFireStartY = wallOfFireAimStartY;
                wallOfFireEndX = wallOfFireAimEndX;
                wallOfFireEndY = wallOfFireAimEndY;
                wallOfFireActive = true;
                wallOfFireFramesRemaining = WALL_OF_FIRE_DURATION;
                wallOfFireCooldownCounter = abilityCooldownTime;
                wallOfFireDamageCooldowns.clear();
            }
        }
        if (potionKeyPressed && !lightningKeyDown && !wallOfFireKeyDown
                && healingPotionCount > 0 && health < MAX_HEALTH) {
            health = Math.min(health + 50, MAX_HEALTH);
            healingPotionCount--;
        }

    }

    private void updateLightningAim() {
        double cameraX = map == null ? 0 : map.getCamera().getX();
        double cameraY = map == null ? 0 : map.getCamera().getY();
        lightningAimX = Mouse.getMouseX() + cameraX;
        lightningAimY = Mouse.getMouseY() + cameraY;
    }

    private void castLightningStrike() {
        lightningStrikeX = lightningAimX;
        lightningStrikeY = lightningAimY;
        lightningStrikeActive = true;
        lightningFramesRemaining = LIGHTNING_EFFECT_DURATION;
        lightningCooldownCounter = abilityCooldownTime;

        if (map == null) {
            return;
        }

        for (NPC npc : map.getActiveNPCs()) {
            Rectangle bounds = npc.getBounds();
            double npcCenterX = bounds.getX() + bounds.getWidth() / 2.0;
            double npcCenterY = bounds.getY() + bounds.getHeight() / 2.0;
            double npcRadius = Math.max(bounds.getWidth(), bounds.getHeight()) / 2.0;
            if (Math.hypot(npcCenterX - lightningStrikeX, npcCenterY - lightningStrikeY)
                    <= LIGHTNING_RADIUS + npcRadius) {
                npc.takeDamage(lightningDamage);
            }
        }
    }

    private void updateLightningStrike() {
        if (lightningStrikeActive && --lightningFramesRemaining <= 0) {
            lightningStrikeActive = false;
        }
    }

    private void updateWallOfFireAim() {
        double playerScreenX = getCalibratedXLocation() + getWidth() / 2.0;
        double playerScreenY = getCalibratedYLocation() + getHeight() / 2.0;
        double dx = Mouse.getMouseX() - playerScreenX;
        double dy = Mouse.getMouseY() - playerScreenY;
        double angle = Math.atan2(dy, dx);
        double lineLength = Math.min(Math.hypot(dx, dy), WALL_OF_FIRE_RANGE);

        wallOfFireAimStartX = getX() + getWidth() / 2.0;
        wallOfFireAimStartY = getY() + getHeight() / 2.0;
        wallOfFireAimEndX = wallOfFireAimStartX + Math.cos(angle) * lineLength;
        wallOfFireAimEndY = wallOfFireAimStartY + Math.sin(angle) * lineLength;
    }

    private void updateWallOfFire() {
        if (!wallOfFireActive) {
            return;
        }

        if (map != null) {
            for (NPC npc : map.getActiveNPCs()) {
                int damageCooldown = wallOfFireDamageCooldowns.getOrDefault(npc, 0);
                if (damageCooldown > 0) {
                    wallOfFireDamageCooldowns.put(npc, damageCooldown - 1);
                    continue;
                }

                Rectangle bounds = npc.getBounds();
                double npcCenterX = bounds.getX() + bounds.getWidth() / 2.0;
                double npcCenterY = bounds.getY() + bounds.getHeight() / 2.0;
                double hitDistance = distanceFromPointToWall(npcCenterX, npcCenterY);
                double npcRadius = Math.max(bounds.getWidth(), bounds.getHeight()) / 2.0;

                if (hitDistance <= WALL_OF_FIRE_WIDTH / 2.0 + npcRadius) {
                    npc.takeDamage(wallOfFireDamage);
                    wallOfFireDamageCooldowns.put(npc, WALL_OF_FIRE_DAMAGE_INTERVAL);
                }
            }
        }

        wallOfFireFramesRemaining--;
        if (wallOfFireFramesRemaining <= 0) {
            wallOfFireActive = false;
            wallOfFireDamageCooldowns.clear();
        }
    }

    private double distanceFromPointToWall(double pointX, double pointY) {
        double lineX = wallOfFireEndX - wallOfFireStartX;
        double lineY = wallOfFireEndY - wallOfFireStartY;
        double lineLengthSquared = lineX * lineX + lineY * lineY;
        double projection = ((pointX - wallOfFireStartX) * lineX
                + (pointY - wallOfFireStartY) * lineY) / lineLengthSquared;
        projection = Math.max(0, Math.min(1, projection));

        double closestX = wallOfFireStartX + projection * lineX;
        double closestY = wallOfFireStartY + projection * lineY;
        return Math.hypot(pointX - closestX, pointY - closestY);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        if (lightningStrikeActive) {
            drawLightningTarget(graphicsHandler, lightningStrikeX, lightningStrikeY, true);
        }
        if (isAimingLightning) {
            drawLightningTarget(graphicsHandler, lightningAimX, lightningAimY, false);
        }
        if (wallOfFireActive) {
            drawWallOfFireLine(graphicsHandler,
                    wallOfFireStartX, wallOfFireStartY, wallOfFireEndX, wallOfFireEndY,
                    new Color(255, 70, 10, 220), WALL_OF_FIRE_WIDTH);
        }
        if (isAimingWallOfFire) {
            drawWallOfFireLine(graphicsHandler,
                    wallOfFireAimStartX, wallOfFireAimStartY, wallOfFireAimEndX, wallOfFireAimEndY,
                    new Color(255, 170, 40, 180), 3);
        }
    }

    private void drawLightningTarget(GraphicsHandler graphicsHandler, double worldX, double worldY,
            boolean strike) {
        double cameraX = map == null ? 0 : map.getCamera().getX();
        double cameraY = map == null ? 0 : map.getCamera().getY();
        int screenX = (int) Math.round(worldX - cameraX);
        int screenY = (int) Math.round(worldY - cameraY);
        int diameter = LIGHTNING_RADIUS * 2;
        Graphics2D graphics = graphicsHandler.getGraphics();
        Color previousColor = graphics.getColor();
        Stroke previousStroke = graphics.getStroke();
        try {
            if (strike) {
                graphics.setColor(new Color(255, 235, 70, 55));
                graphics.fillOval(screenX - LIGHTNING_RADIUS, screenY - LIGHTNING_RADIUS,
                        diameter, diameter);
                graphics.setColor(new Color(255, 235, 100, 210));
                graphics.setStroke(new BasicStroke(18, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                graphics.drawLine(screenX, screenY - 180, screenX, screenY);
                graphics.setColor(new Color(255, 255, 220, 240));
                graphics.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                graphics.drawLine(screenX, screenY - 180, screenX, screenY);
            } else {
                graphics.setColor(new Color(255, 235, 70, 45));
                graphics.fillOval(screenX - LIGHTNING_RADIUS, screenY - LIGHTNING_RADIUS,
                        diameter, diameter);
                graphics.setColor(new Color(255, 245, 120, 220));
                graphics.setStroke(new BasicStroke(2));
                graphics.drawOval(screenX - LIGHTNING_RADIUS, screenY - LIGHTNING_RADIUS,
                        diameter, diameter);
            }
        } finally {
            graphics.setColor(previousColor);
            graphics.setStroke(previousStroke);
        }
    }

    private void drawWallOfFireLine(GraphicsHandler graphicsHandler,
            double startX, double startY, double endX, double endY, Color color, int width) {
        double cameraX = map == null ? 0 : map.getCamera().getX();
        double cameraY = map == null ? 0 : map.getCamera().getY();
        Graphics2D graphics = graphicsHandler.getGraphics();
        Color previousColor = graphics.getColor();
        Stroke previousStroke = graphics.getStroke();
        try {
            graphics.setColor(color);
            graphics.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.drawLine(
                    (int) Math.round(startX - cameraX),
                    (int) Math.round(startY - cameraY),
                    (int) Math.round(endX - cameraX),
                    (int) Math.round(endY - cameraY));
        } finally {
            graphics.setColor(previousColor);
            graphics.setStroke(previousStroke);
        }
    }

    // anything extra the player should do based on interactions can be handled here
 
    /*protected void handlePlayerAnimation() {
        if (playerState == PlayerState.STATIC) {
            if (facingDirection == Direction.RIGHT) {
                this.currentAnimationName = "STAND_RIGHT";
            }
            else if (facingDirection == Direction.LEFT) {
                this.currentAnimationName = "STAND_LEFT";
            }
            else {
            this.currentAnimationName = "STATIC";
            }
        }
        else if (playerState == PlayerState.MOVE) {
             if (facingDirection == Direction.LEFT) {
                this.currentAnimationName = "MOVE_LEFT";
            }
            else if (facingDirection == Direction.RIGHT) {
                this.currentAnimationName = "MOVE_RIGHT";
            }
            else if (facingDirection == Direction.UP) {
                this.currentAnimationName = "MOVE_UP";
            }
            else if (facingDirection == Direction.DOWN) {
                this.currentAnimationName = "MOVE_DOWN";
            }
            else {
                this.currentAnimationName = "STATIC";
            }
        }
    }*/
   protected void handlePlayerAnimation() {
    if (isAttacking) {
        return;
    }
    if (playerState == PlayerState.STATIC) {

        if (facingDirection == Direction.LEFT) {
            this.currentAnimationName = "STAND_LEFT";
        }
        else {
            this.currentAnimationName = "STAND_RIGHT";
        }

    }
    else if (playerState == PlayerState.MOVE) {

        if (facingDirection == Direction.LEFT) {
            this.currentAnimationName = "MOVE_LEFT";
        }
        else {
            this.currentAnimationName = "MOVE_RIGHT";
        }

    }
}


    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, GameObject entityCollidedWith) { }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, GameObject entityCollidedWith) { }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public Rectangle getInteractionRange() {
        return new Rectangle(
                getBounds().getX1() - interactionRange,
                getBounds().getY1() - interactionRange,
                getBounds().getWidth() + (interactionRange * 2),
                getBounds().getHeight() + (interactionRange * 2));
    }


    public int getHealth() {
            return health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public void heal(int amount) {
        if (amount > 0) {
            health = Math.min(MAX_HEALTH, health + amount);
        }
    }

    public void takeDamage(int amount) {

        if (health <= 0 || amount <= 0 || isRolling()) {
            return;
        }

        health -= amount;

        if (health <= 0) {
            health = 0;

            if (map != null) {
                float effectSize = 128;

                float effectX = getX()
                    + (getWidth() - effectSize) / 2;

                float effectY = getY()
                    + (getHeight() - effectSize) / 2;

                map.addDeathEffect(effectX, effectY);
            }
        }
        else {
            startDamageFlash();
        }
    }

    public Key getInteractKey() { return INTERACT_KEY; }
    public Direction getCurrentWalkingXDirection() { return currentWalkingXDirection; }
    public Direction getCurrentWalkingYDirection() { return currentWalkingYDirection; }
    public Direction getLastWalkingXDirection() { return lastWalkingXDirection; }
    public Direction getLastWalkingYDirection() { return lastWalkingYDirection; }

    
    public void lock() {
        isLocked = true;
        playerState = PlayerState.STATIC;
        //this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
        this.currentAnimationName = "STATIC";
    }

    public void unlock() {
        isLocked = false;
        playerState = PlayerState.STATIC;
        //this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
        this.currentAnimationName = "STATIC";
    }

    // used by other files or scripts to force player to stand
    public void stand(Direction direction) {
        playerState = PlayerState.STATIC;
        facingDirection = direction;
        this.currentAnimationName = "STATIC";
        /* 
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "STAND_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "STAND_LEFT";
        }
        */
    }

    // used by other files or scripts to force player to walk
    public void walk(Direction direction, float speed) {
        playerState = PlayerState.MOVE;
        facingDirection = direction;
       playerState = PlayerState.MOVE;

if (direction == Direction.RIGHT) {
    facingDirection = Direction.RIGHT;
}
else if (direction == Direction.LEFT) {
    facingDirection = Direction.LEFT;
}

if (facingDirection == Direction.LEFT) {
    this.currentAnimationName = "MOVE_LEFT";
}
else {
    this.currentAnimationName = "MOVE_RIGHT";
}

        if (direction == Direction.UP) {
            moveY(-speed);
        }
        else if (direction == Direction.DOWN) {
            moveY(speed);
        }
        else if (direction == Direction.LEFT) {
            moveX(-speed);
        }
        else if (direction == Direction.RIGHT) {
            moveX(speed);
        }
    }

    public Map getMap()
    {
        return map;
    }

    // Uncomment this to have game draw player's bounds to make it easier to visualize
    /*
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }
    */
}
