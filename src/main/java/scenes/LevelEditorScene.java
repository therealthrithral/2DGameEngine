package scenes;


import components.*;
import imgui.ImGui;
import imgui.ImVec2;
import jade.*;
import jade.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import renderer.DebugDraw;

public class LevelEditorScene extends Scene {
    private GameObject obj1;
    private Spritesheet sprites;
    private SpriteRenderer obj1Sprite;

    GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);

    public LevelEditorScene() {


    }

    @Override
    public void init(){
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png");

        if (levelLoaded){
            this.activeGameObject = gameObjects.getFirst();
            return;
        }
        // Debug Squares
//        obj1 = new GameObject("Object 1", new Transform(new Vector2f(200,100),
//                new Vector2f(256,256)),2);
//        obj1Sprite = new SpriteRenderer();
//        obj1Sprite.setColor(new Vector4f(1,0,0,1));
//        obj1.addComponent(obj1Sprite);
//        obj1.addComponent(new RigidBody());
//
//        this.addGameObjectToScene(obj1);
//        this.activeGameObject = obj1;
//
//        GameObject obj2 = new GameObject("Object 2",
//                new Transform(new Vector2f(400,100), new Vector2f(256,256)), 1);
//
//        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
//        Sprite obj2Sprite = new Sprite();
//        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
//        obj2SpriteRenderer.setSprite(obj2Sprite);
//
//        obj2.addComponent(obj2SpriteRenderer);
//        this.addGameObjectToScene(obj2);
    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");


        AssetPool.addSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"),
                        16,16,81,0));

        AssetPool.getTexture("assets/images/blendImage2.png");

    }
    float x = 0.0f;
    float y = 0.0f;
    //float angle = 0.0f;
    // Float for Debug Drawings
    //float t = 0.0f;
    @Override
    public void update(float dt) {
        levelEditorStuff.update(dt);
        DebugDraw.addCircle(new Vector2f(x,y), 64, new Vector3f(0,1,0), 1);
        x += .50f + dt;
        y += .50f + dt;
        // Debug Drawings
//        float x = ((float)Math.sin(t) * 200.0f) + 600;
//        float y = ((float)Math.cos(t) * 200.0f) + 400;
//        t += 0.05f;
//        DebugDraw.addLine2D(new Vector2f(600, 400), new Vector2f(x,y), new Vector3f(0,0,1), 10);



        // FPS
        //System.out.println("FPS " + (1.0f/dt));

        // Move Sprite obj1
        //obj1.transform.position.x += 10*dt;


        for (GameObject go: this.gameObjects){
            go.update(dt);
        }
        this.renderer.render();
    }
    @Override
    public void imgui(){
        ImGui.begin("Test Window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i< sprites.size(); i++){
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if(ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)){
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
                // Attach this to mouse cursor
                levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if(i + 1 < sprites.size() && nextButtonX2 < windowX2){
                ImGui.sameLine();
            }
        }


        ImGui.end();
    }
}