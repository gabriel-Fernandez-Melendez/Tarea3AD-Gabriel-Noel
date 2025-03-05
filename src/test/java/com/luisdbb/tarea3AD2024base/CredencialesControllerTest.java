package com.luisdbb.tarea3AD2024base;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

import java.lang.annotation.Target;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import com.Gabriel.Noel.tarea3AD2024base.config.AppJavaConfig;
import com.Gabriel.Noel.tarea3AD2024base.config.StageManager;
import com.Gabriel.Noel.tarea3AD2024base.controller.CredencialesController;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class) // esta llamada permite organizar los componentes de javafx con sus valores
@MockitoSettings(strictness = Strictness.LENIENT) // correctos
public class CredencialesControllerTest {

	@Mock
	CredencialesController CredencialesController;

	@Mock
	private TextField nombreUsuario;
	@Mock
	private PasswordField contraseña;
	@Mock
	private Button Boton_login;
	@Mock
	private Button boton_Nuevo_Peregrino;

	@Mock
	private MenuItem ayuda;

	// aqui cargamos el valor de los campos que queremos mapear dentro del resto de
	// nuestros test
	CredencialesController spyLoaderController;

	@BeforeEach
	public void Carga() {
		/*
		 * MockitoAnnotations.openMocks(this); spyLoaderController =
		 * spy(CredencialesController); spyLoaderController.Boton_login = Boton_login;
		 * spyLoaderController.nombreUsuario = nombreUsuario;
		 * spyLoaderController.contraseña = contraseña;
		 * spyLoaderController.boton_Nuevo_Peregrino = boton_Nuevo_Peregrino;
		 * spyLoaderController.ayuda = ayuda;
		 */

	}

	@Test
	public void testAlwaysTrue() {
		// This assertion will always pass
		assertTrue(true);
	}
}
