package com.Gabriel.Noel.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	},
	RESPONSABLE{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("responsable.title");
		}
		@Override
		public String getFxmlFile() {
			return "/fxml/ResponsableParada.fxml";
		}
	},
	
	RECUPERAR_CONTRASEÑA{
		@Override
		public String getTitle()
		{
			return getStringFromResourceBundle("contraseña.title");
		}
		@Override
		public String getFxmlFile()
		{
			return "/fxml/RecordarContraseña.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
