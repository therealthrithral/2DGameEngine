package components;

import jade.Camera;
import jade.Window;
import jade.util.Settings;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderer.DebugDraw;

public class GridLines extends Component {

    @Override
    public void update(float dt) {
        Camera camera =  Window.getScene().camera();
        Vector2f cameraPos = Window.getScene().camera().position;
        Vector2f projectionSize = Window.getScene().camera().getProjectionSize();

        int firstX = ((int)Math.floor(cameraPos.x / Settings.GRID_WIDTH)) * Settings.GRID_WIDTH;
        int firstY = ((int)Math.floor(cameraPos.y / Settings.GRID_HEIGHT)) * Settings.GRID_HEIGHT;

        int numVtLines = (int) (projectionSize.x / Settings.GRID_WIDTH) + 2;
        int numHzLines = (int) (projectionSize.y / Settings.GRID_HEIGHT) + 2;

        float width = (int)(projectionSize.x  * camera.getZoom()) + (5 * Settings.GRID_WIDTH);
        float height = (int)(projectionSize.y * camera.getZoom()) + (5 * Settings.GRID_HEIGHT);

        int maxLines = Math.max(numVtLines, numHzLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i = 0; i < maxLines; i++) {
            float x = firstX + (Settings.GRID_WIDTH * i);
            float y = firstY + (Settings.GRID_HEIGHT * i);

            if (i < numVtLines) {
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, firstY + height), color);
            }
            if (i < numHzLines) {
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }
    }
}
