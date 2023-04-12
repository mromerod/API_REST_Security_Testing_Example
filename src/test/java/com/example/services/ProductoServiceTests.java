package com.example.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.dao.PresentacionDao;
import com.example.dao.ProductoDao;
import com.example.entities.Presentacion;
import com.example.entities.Producto;

import static org.assertj.core.api.Assertions.assertThat;

// Para seguir el enfoque de BDD con Mockito
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductoServiceTests {
    
    @Mock
    private ProductoDao productoDao;

    @Mock
    private PresentacionDao presentacionDao;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        Presentacion presentacion = Presentacion.builder()
            .descripcion(null)
            .nombre("unidades")
            .build();

        producto = Producto.builder()
            .id(20L)
            .nombre("Google Pixel 7")
            .descripcion("Telefono de Google")
            .precio(800.00)
            .stock(1000)
            .imagenProducto(null)
            .presentacion(presentacion)
            .build();
    }

    @Test
    @DisplayName("Test de servicio para persistir un producto")
    public void testGuardarProducto() {

        // given 
        given(productoDao.save(producto)).willReturn(producto);

        // when
        Producto productoGuardado = productoService.save(producto);

        // then
        assertThat(productoGuardado).isNotNull();
    }

    @DisplayName("Recupera una lista vacia de productos")
    @Test
    public void testEmptyProductList() {

        // given
        given(productoDao.findAll()).willReturn(Collections.emptyList());

        // when
        List<Producto> productos = productoDao.findAll();

        // then
        assertThat(productos).isEmpty();
    }
}
