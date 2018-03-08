package LAB10;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Slots extends Application {

	// Arrays
	Image[] images;
	ImageView[] views;

	TextField input;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage gui) throws Exception {
		GridPane grid = new GridPane();
		Button btn = new Button();
		Label lbl = new Label();
		init(grid, btn, lbl);

		spin();

		// EVENT HANDLER
		btn.setOnAction(event -> {
			spin();

			// Running Total
			double amount = 0;
			boolean valid = true;
			try {
				amount = Double.parseDouble(input.getText());
			} catch (NumberFormatException e) {
				valid = false;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Input");
				alert.setHeaderText("Cannot Parse " + input.getText());
				alert.setContentText("Retry Input");
				alert.showAndWait();
			}

			// if
			if (valid) {
				// if else
				if (views[0].getImage() == views[1].getImage() && views[0].getImage() == views[2].getImage()) {
					amount *= 3;
					lbl.setText("You won: " + String.format("$%,.2f", amount) + "\n3 of a kind");
				} else if (views[0].getImage() == views[1].getImage() || views[0].getImage() == views[2].getImage()
						|| views[1].getImage() == views[2].getImage()) {
					amount *= 2;
					lbl.setText("You won: " + String.format("$%,.2f", amount) + "\n2 of a kind");
				} else {
					lbl.setText("You lost: " + String.format("$%,.2f", amount));
				}
			}

		});

		// BUILD
		Scene container = new Scene(grid, 750, 400);
		gui.setScene(container);
		gui.show();
	}

	public void init(GridPane grid, Button btn, Label lbl) throws FileNotFoundException {
		// Components
		lbl.setText("Spin To Win");
		btn.setText("Spin");
		input = new TextField("0");
		input.setMaxWidth(80);

		// Images
		images = new Image[5];
		images[0] = new Image(new FileInputStream("src\\LAB10\\seven.png"));
		images[1] = new Image(new FileInputStream("src\\LAB10\\cherry.png"));
		images[2] = new Image(new FileInputStream("src\\LAB10\\orange.png"));
		images[3] = new Image(new FileInputStream("src\\LAB10\\apple.png"));
		images[4] = new Image(new FileInputStream("src\\LAB10\\lemon.png"));

		// Build board all empty
		views = new ImageView[3];
		for (int i = 0; i < views.length; i++) {
			views[i] = new ImageView();
			grid.add(views[i], (1 + i), 0);
		}

		// add to grid
		grid.add(input, 1, 1);
		grid.add(btn, 2, 1);
		grid.add(lbl, 3, 1);

		grid.setHgap(20);
	}

	public void spin() {
		for (int i = 0; i < views.length; i++) {
			views[i].setImage(images[(int) (Math.random() * (images.length - 1))]);
		}
	}

}
