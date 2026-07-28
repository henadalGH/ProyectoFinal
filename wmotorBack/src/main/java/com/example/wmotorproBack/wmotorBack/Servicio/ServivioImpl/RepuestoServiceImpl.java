package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.AjusteDePrecioDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ModificarPrecioPorMarca;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerNombreMarcaRepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ObtenerRepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.RepuestoStockBajoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.StockRepuestoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.RepuestosEntity;
import com.example.wmotorproBack.wmotorBack.Repository.RepuestoRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.RepuestoService;

@Service
public class RepuestoServiceImpl implements RepuestoService {

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Override
    public List<RepuestoDTO> obtenerRepuesto() {

        return repuestoRepository.findAll()
        .stream()
            .map(this::toMapRepuesto)
            .collect(Collectors.toList());
    }

    @Override
    public RepuestoDTO toMapRepuesto(RepuestosEntity repuesto) {
        
        RepuestoDTO repuestoDTO = new RepuestoDTO();
        repuestoDTO.setId(repuesto.getId());
        repuestoDTO.setNombre(repuesto.getNombre());
        repuestoDTO.setMarca(repuesto.getMarca());
        repuestoDTO.setCodigo(repuesto.getCodigo());
        repuestoDTO.setStock(repuesto.getStock());
        repuestoDTO.setStockMin(repuesto.getStockMin());
        repuestoDTO.setPrecioCompra(repuesto.getPrecioCompra());
        repuestoDTO.setPrecioVenta(repuesto.getPrecioVenta());
        repuestoDTO.setActivo(repuesto.getActivo());
        repuestoDTO.setCategoria(repuesto.getCategoria());

        return repuestoDTO;
    }

    @Override
    public RepuestoDTO obtenerRepuestoPorId(Long idRepuesto) {

        RepuestosEntity repuestosEntity = repuestoRepository.findById(idRepuesto)
        .orElseThrow(() -> new RuntimeException("Id repuesto no encontrado"));

        return toMapRepuesto(repuestosEntity);        
        
    }

    @Override
    public ResponceDTO aumentarStok(Long idRepuesto, StockRepuestoDTO stockRepuestoDTO) {
        
        ResponceDTO responceDTO = new ResponceDTO();
        
        RepuestosEntity repuestosEntity = repuestoRepository.findById(idRepuesto)
        .orElseThrow(() -> new RuntimeException("Id repuesto no encontrado"));

        
        repuestosEntity.setStock(repuestosEntity.getStock() + stockRepuestoDTO.getCantidad());

        repuestoRepository.save(repuestosEntity);

        responceDTO.setMensage("El stock a aumentado correctamente");
        return responceDTO;
    }

    @Override
    public ResponceDTO disminuirStock(Long idRepuesto, StockRepuestoDTO stockRepuestoDTO) {
        ResponceDTO responceDTO = new ResponceDTO();
        
        RepuestosEntity repuestosEntity = repuestoRepository.findById(idRepuesto)
        .orElseThrow(() -> new RuntimeException("Id repuesto no encontrado"));

        repuestosEntity.setStock(repuestosEntity.getStock() - stockRepuestoDTO.getCantidad());

        repuestoRepository.save(repuestosEntity);

        responceDTO.setMensage("El stock a disminuido correctamente");
        return responceDTO;
    }

    @Override
    public ResponceDTO activaDesactivaRepuesto(Long idRepuesto, Boolean activo) {
        
        ResponceDTO responceDTO = new ResponceDTO();
        
        RepuestosEntity repuestosEntity = repuestoRepository.findById(idRepuesto)
        .orElseThrow(() -> new RuntimeException("Id repuesto no encontrado"));

        if (activo == false) {

            repuestosEntity.setActivo(activo);
            repuestoRepository.save(repuestosEntity);    
            
            responceDTO.setMensage("El repuesto ha sido desactivado con exito");
        } else if (activo == true) {

            repuestosEntity.setActivo(activo);
            repuestoRepository.save(repuestosEntity);    
            
            responceDTO.setMensage("El repuesto ha sido acticado con exito");
            
        }

        return responceDTO;
    }

    @Override
    public List<RepuestoStockBajoDTO> obtenerRepuestoBajoStock() {
        return repuestoRepository.buscarStockBajo();
    }

    @Override
    public List<ObtenerRepuestoDTO> obtenerRepuestosOrden() {
        return repuestoRepository.obtenerRepuesto();
    }

    @Override
@Transactional
public ResponceDTO modificarPrecioCompra(ModificarPrecioPorMarca modificarPrecio) {

    ResponceDTO response = new ResponceDTO();

    List<RepuestosEntity> repuestos;

    boolean tieneMarca = modificarPrecio.getMarcaRepuesto() != null &&
                         !modificarPrecio.getMarcaRepuesto().isBlank();

    boolean tieneNombre = modificarPrecio.getNombreRepuesto() != null &&
                          !modificarPrecio.getNombreRepuesto().isBlank();

    if (tieneMarca && tieneNombre) {

        repuestos = repuestoRepository.findByMarcaIgnoreCaseAndNombreIgnoreCase(
                modificarPrecio.getMarcaRepuesto(),
                modificarPrecio.getNombreRepuesto());

    } else if (tieneMarca) {

        repuestos = repuestoRepository.findByMarcaIgnoreCase(
                modificarPrecio.getMarcaRepuesto());

    } else if (tieneNombre) {

        repuestos = repuestoRepository.findByNombreIgnoreCase(
                modificarPrecio.getNombreRepuesto());

    } else {
        throw new RuntimeException("Debe ingresar una marca, un nombre o ambos.");
    }

    if (repuestos.isEmpty()) {
        throw new RuntimeException("No se encontraron repuestos.");
    }

    for (RepuestosEntity repuesto : repuestos) {

        double precioActual = repuesto.getPrecioCompra();
        double nuevoPrecio = precioActual + (precioActual * (modificarPrecio.getPorcentaje() / 100.0));

        repuesto.setPrecioCompra(nuevoPrecio);

        // 50% de ganancia
        repuesto.setPrecioVenta(nuevoPrecio * 1.5);
    }

    repuestoRepository.saveAll(repuestos);

    response.setMensage("Precios actualizados correctamente.");
    return response;
}

    @Override
public ResponceDTO crearRepuesto(RepuestoDTO repuestoDTO) {

    ResponceDTO responceDTO = new ResponceDTO();

    RepuestosEntity repuestosEntity = new RepuestosEntity();

    repuestosEntity.setNombre(repuestoDTO.getNombre());
    repuestosEntity.setMarca(repuestoDTO.getMarca());
    repuestosEntity.setCodigo(repuestoDTO.getCodigo());
    repuestosEntity.setPrecioCompra(repuestoDTO.getPrecioCompra());
    repuestosEntity.setStock(repuestoDTO.getStock());
    repuestosEntity.setStockMin(repuestoDTO.getStockMin());
    repuestosEntity.setCategoria(repuestoDTO.getCategoria());
    repuestosEntity.setActivo(true);


    // Si no viene porcentaje usa 50% por defecto
    Double porcentajeGanancia = repuestoDTO.getPorcentajeGanancia();

    if (porcentajeGanancia == null) {
        porcentajeGanancia = 50.0;
    }


    // Calcular precio de venta
    Double precioVenta = repuestoDTO.getPrecioCompra()
            + (repuestoDTO.getPrecioCompra() * porcentajeGanancia / 100);


    // Redondear a 2 decimales
    precioVenta = Math.round(precioVenta * 100.0) / 100.0;


    repuestosEntity.setPrecioVenta(precioVenta);


    repuestoRepository.save(repuestosEntity);


    responceDTO.setMensage("Repuesto creado correctamente");

    return responceDTO;
}

    @Override
    public List<ObtenerNombreMarcaRepuestoDTO> obtenerNombreRepuestos() {
        return repuestoRepository.obtenerNombreMarca();
    }

   @Override
   @Transactional
public ResponceDTO ajustarPreciosRepuestos(AjusteDePrecioDTO dto) {

    ResponceDTO responceDTO = new ResponceDTO();


    if (dto.getPorcentaje() == null || dto.getPorcentaje() <= 0) {
        responceDTO.setMensage("El porcentaje debe ser mayor a cero");
        return responceDTO;
    }


    if (dto.getTipoDescuento() == null) {
        responceDTO.setMensage("Debe indicar si es AUMENTO o DESCUENTO");
        return responceDTO;
    }


    List<RepuestosEntity> repuestos = repuestoRepository.findAll();


    for (RepuestosEntity repuesto : repuestos) {

        Double precioActual = repuesto.getPrecioVenta();


        Double ajuste = precioActual * dto.getPorcentaje() / 100;


        Double nuevoPrecio;


        if (dto.getTipoDescuento().equalsIgnoreCase("AUMENTO")) {

            nuevoPrecio = precioActual + ajuste;


        } else if (dto.getTipoDescuento().equalsIgnoreCase("DESCUENTO")) {

            nuevoPrecio = precioActual - ajuste;


        } else {

            responceDTO.setMensage("Tipo inválido. Use AUMENTO o DESCUENTO");
            return responceDTO;
        }
        // Redondear a 2 decimales
        nuevoPrecio = Math.round(nuevoPrecio * 100.0) / 100.0;
        repuesto.setPrecioVenta(nuevoPrecio);
    }
    repuestoRepository.saveAll(repuestos);
    responceDTO.setMensage("Precios actualizados correctamente");

    return responceDTO;
}

}
