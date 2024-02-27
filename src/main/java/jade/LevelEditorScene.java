package jade;


import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import jade.util.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {


    }

    @Override
    public void init(){
        loadResources();

        Spritesheet sprites = AssetPool.getSpriteSheet("assets/images/spritesheet.png");

        this.camera = new Camera(new Vector2f());

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100,100), new Vector2f(256,256)));
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

    @Override
    public void update(float dt) {
        // FPS
        //System.out.println("FPS " + (1.0f/dt));

        for (GameObject go: this.gameObjects){
            go.update(dt);
        }
        this.renderer.render();
    }
}