package jade;


import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import jade.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {
    private GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene() {


    }

    @Override
    public void init(){
        loadResources();

        sprites = AssetPool.getSpriteSheet("assets/images/spritesheet.png");

        this.camera = new Camera(new Vector2f());

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100,100), new Vector2f(256,256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400,100), new Vector2f(256,256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(17)));
        this.addGameObjectToScene(obj2);

    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16,16,26,0));

    }
    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float dt) {
        // FPS
        //System.out.println("FPS " + (1.0f/dt));

        // Move Sprite obj1
        //obj1.transform.position.x += 10*dt;
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft<=0){
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex++;
            if(spriteIndex>4){
                spriteIndex=0;
            }
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        }

        for (GameObject go: this.gameObjects){
            go.update(dt);
        }
        this.renderer.render();
    }
}