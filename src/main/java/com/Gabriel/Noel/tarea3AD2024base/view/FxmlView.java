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
			return "/fxml/Credenciales.fxml";
		}
	},
	RESPONSABLE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("responsable.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ResponsableParada.fxml";
		}
	},

	RECUPERAR_CONTRASEÑA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("contraseña.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RecordarContraseña.fxml";
		}
	},

	ESTANCIAS_FILTRADAS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("Estancias_Filtradas.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EstanciasFiltradas.fxml";
		}
	},
	NuevoPeregrino {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("NuevoPeregrino.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/NuevoPeregrino.fxml";
		}
	},
	ExportarXML {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("ExportarCarnetXML.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ExportarCarnetXML.fxml";
		}
	}

	,
	NuevaParada {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("NuevaParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/NuevaParada.fxml";
		}
	},
	
	ServiciosAdministrador {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("NuevaParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ServiciosAdministrador.fxml";
		}
	},
	
	EnvioaCasa {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("NuevaParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EnvioACasa.fxml";
		}
	},
	
	EnviosRealizados {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("NuevaParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EnviosRealizados.fxml";
		}
	},
	EDITARSERVICIOS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("NuevaParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/EditarServicios.fxml";
		}
	}
	
	;

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
