package Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Controller
{
	@FXML private Text byJesseTxt;
	@FXML private Button startBtn;
	@FXML private ImageView avatarView;
	@FXML private Circle statusCircle;
	@FXML private Text statusTxt;

	public void startBot()
	{
		try
		{
			KellyBot.turnOn();
			startBtn.setVisible(false);

			statusTxt.setText("Online");
			statusCircle.setFill(javafx.scene.paint.Color.valueOf("#43b581")); // Offline: #c20606, Invisible: #747f8d, Do Not Disturb: #f04747, Idle: #faa61a, Online: #43b581
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
