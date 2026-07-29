package com.example.wmotorproBack.wmotorBack.Servicio.ServivioImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wmotorproBack.wmotorBack.Modelo.DTO.BalanceMensualDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.MovimientoDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.ResponceDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.DTO.UltimosMovimientosDTO;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.AdminEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CargosEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.CategoriaMovimientoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.EmpleadoEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Entity.MovimientoFinancieroEntity;
import com.example.wmotorproBack.wmotorBack.Modelo.Enums.MovimientosEnum;
import com.example.wmotorproBack.wmotorBack.Repository.AdminRepository;
import com.example.wmotorproBack.wmotorBack.Repository.CategoriaMovimientoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.EmpleadoRepository;
import com.example.wmotorproBack.wmotorBack.Repository.MovimientoFinancieroRepository;
import com.example.wmotorproBack.wmotorBack.Servicio.MovimientoFinService;

@Service
public class MovimientoFinServiceImpl implements MovimientoFinService {

    @Autowired
    private MovimientoFinancieroRepository movimientoFinancieroRepository;

    @Autowired
    private CategoriaMovimientoRepository categoriaMovimientoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<MovimientoFinancieroEntity> getAllMovimiento() {
        return movimientoFinancieroRepository.findAll();
    }

    @Override
    public ResponceDTO crearMovimiento(MovimientoDTO movimiento) throws Exception {


        // Buscar categoría
        CategoriaMovimientoEntity categoria =
                categoriaMovimientoRepository
                        .findByCategoria(movimiento.getCategoria())
                        .orElseThrow(() ->
                                new Exception(
                                        "Categoría no encontrada: "
                                                + movimiento.getCategoria()));

        // Convertir tipo
        MovimientosEnum tipoMovimiento =
                MovimientosEnum.valueOf(
                        movimiento.getTipoMovimiento()
                                .toUpperCase());

        // Validar categoría
        if (categoria.getTipoMovimiento() != tipoMovimiento) {
            throw new Exception(
                    "La categoría "
                            + categoria.getCategoria()
                            + " no corresponde al tipo "
                            + tipoMovimiento);
        }

        // Buscar admin
        AdminEntity admin =
                adminRepository
                        .findById(movimiento.getIdAdmin())
                        .orElseThrow(() ->
                                new Exception(
                                        "Administrador no encontrado"));

        // Crear entidad
        MovimientoFinancieroEntity entity =
                new MovimientoFinancieroEntity();

        entity.setTipoMovimiento(tipoMovimiento);
        entity.setConcepto(movimiento.getConcepto());
        entity.setImporte(movimiento.getImporte());
        entity.setFechaRegistro(LocalDate.now());
        entity.setCategoria(categoria);
        entity.setAdmin(admin);

        movimientoFinancieroRepository.save(entity);

        ResponceDTO response = new ResponceDTO();
        response.setNumOfErrors(0);
        response.setMensage(
                "Movimiento registrado correctamente");

        return response;
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerPorFecha(
            LocalDate fecha) {

        return movimientoFinancieroRepository
                .findByFechaRegistro(fecha);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerEntreFechas(
            LocalDate fechaInicio,
            LocalDate fechaFin) {

        return movimientoFinancieroRepository
                .findByFechaRegistroBetween(
                        fechaInicio,
                        fechaFin);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerPorMes(
            int mes,
            int anio) {

        LocalDate inicio =
                LocalDate.of(anio, mes, 1);

        LocalDate fin =
                inicio.withDayOfMonth(
                        inicio.lengthOfMonth());

        return movimientoFinancieroRepository
                .findByFechaRegistroBetween(
                        inicio,
                        fin);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerPorCategoria(
            Long idCategoria) {

        return movimientoFinancieroRepository
                .findByCategoriaId(idCategoria);
    }

    @Override
    public List<MovimientoFinancieroEntity> obtenerPorTipo(
            String tipoMovimiento) {

        MovimientosEnum tipo =
                MovimientosEnum.valueOf(
                        tipoMovimiento.toUpperCase());

        return movimientoFinancieroRepository
                .findByTipoMovimiento(tipo);
    }

    @Override
    public Double totalIngresos(
            LocalDate desde,
            LocalDate hasta) {

        return movimientoFinancieroRepository
                .findByFechaRegistroBetween(
                        desde,
                        hasta)
                .stream()
                .filter(m ->
                        m.getTipoMovimiento()
                                == MovimientosEnum.INGRESO)
                .mapToDouble(
                        MovimientoFinancieroEntity::getImporte)
                .sum();
    }

    @Override
    public Double totalEgresos(
            LocalDate desde,
            LocalDate hasta) {

        return movimientoFinancieroRepository
                .findByFechaRegistroBetween(
                        desde,
                        hasta)
                .stream()
                .filter(m ->
                        m.getTipoMovimiento()
                                == MovimientosEnum.GASTOS)
                .mapToDouble(
                        MovimientoFinancieroEntity::getImporte)
                .sum();
    }

    @Override
    public Double balanceGeneral(
            LocalDate desde,
            LocalDate hasta) {

        return totalIngresos(desde, hasta)
                - totalEgresos(desde, hasta);
    }

    @Override
    public List<BalanceMensualDTO> obtenerBalanceMensual(int anio) {
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);

        List<MovimientoFinancieroEntity> movimientos = movimientoFinancieroRepository
                .findByFechaRegistroBetween(inicio, fin);

        List<BalanceMensualDTO> balances = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            BalanceMensualDTO dto = new BalanceMensualDTO(mes);

            for (MovimientoFinancieroEntity movimiento : movimientos) {
                if (movimiento.getFechaRegistro().getMonthValue() != mes) {
                    continue;
                }

                if (movimiento.getTipoMovimiento() == MovimientosEnum.INGRESO) {
                    dto.setTotalIngresos(dto.getTotalIngresos() + movimiento.getImporte());
                } else if (movimiento.getTipoMovimiento() == MovimientosEnum.GASTOS) {
                    dto.setTotalGastos(dto.getTotalGastos() + movimiento.getImporte());
                }
            }

            dto.setBalance(dto.getTotalIngresos() - dto.getTotalGastos());
            balances.add(dto);
        }

        return balances;
    }

    @Override
    public List<UltimosMovimientosDTO> ultimosMovimientos() {

        return movimientoFinancieroRepository.findTop10ByOrderByFechaRegistroDesc()
        .stream()
        .map( m -> {
                UltimosMovimientosDTO dto = new UltimosMovimientosDTO();

                dto.setTipoMovimiento( m.getTipoMovimiento());
                dto.setCategoria( m.getCategoria().getCategoria());
                dto.setMonto(m.getImporte());
                dto.setFechaRegistro(m.getFechaRegistro());
                dto.setConcepto(m.getConcepto());

                return dto;
        })
        .toList();
    }

    @Override
    public Double totalSueldos() {
        return empleadoRepository.findAll()
                .stream()
                .filter(e -> Boolean.TRUE.equals(e.getActivo()))
                .map(EmpleadoEntity::getCargo)
                .filter(c -> c != null && c.getSueldoBase() != null)
                .mapToDouble(CargosEntity::getSueldoBase)
                .sum();
    }

    @Override
    public boolean sueldosYaPagadosEsteMes() throws Exception {
        CategoriaMovimientoEntity categoria = categoriaMovimientoRepository
                .findByCategoria("Sueldos")
                .orElse(null);

        if (categoria == null) {
            return false;
        }

        LocalDate ahora = LocalDate.now();
        LocalDate mesInicio = LocalDate.of(ahora.getYear(), ahora.getMonthValue(), 1);
        LocalDate mesFin = mesInicio.withDayOfMonth(mesInicio.lengthOfMonth());

        return !movimientoFinancieroRepository
                .findByCategoriaAndTipoMovimientoAndFechaRegistroBetween(
                        categoria,
                        MovimientosEnum.GASTOS,
                        mesInicio,
                        mesFin)
                .isEmpty();
    }

    @Override
    public ResponceDTO pagarSueldos(Long idAdmin) throws Exception {
        long empleadosActivos = empleadoRepository.findAll()
                .stream()
                .filter(e -> Boolean.TRUE.equals(e.getActivo()))
                .count();

        if (empleadosActivos == 0) {
            throw new Exception("No hay empleados activos para registrar el pago de sueldos.");
        }

        Double importeTotal = totalSueldos();

        if (importeTotal == null || importeTotal <= 0) {
            throw new Exception("No hay sueldos activos para registrar.");
        }

        CategoriaMovimientoEntity categoria = categoriaMovimientoRepository
                .findByCategoria("Sueldos")
                .orElseGet(() -> {
                    CategoriaMovimientoEntity nuevaCategoria = new CategoriaMovimientoEntity();
                    nuevaCategoria.setCategoria("Sueldos");
                    nuevaCategoria.setTipoMovimiento(MovimientosEnum.GASTOS);
                    return categoriaMovimientoRepository.save(nuevaCategoria);
                });

        AdminEntity admin = adminRepository
                .findById(idAdmin)
                .orElseThrow(() -> new Exception("Administrador no encontrado"));

        LocalDate ahora = LocalDate.now();
        LocalDate mesInicio = LocalDate.of(ahora.getYear(), ahora.getMonthValue(), 1);
        LocalDate mesFin = mesInicio.withDayOfMonth(mesInicio.lengthOfMonth());

        boolean yaPagado = !movimientoFinancieroRepository
                .findByCategoriaAndTipoMovimientoAndFechaRegistroBetween(
                        categoria,
                        MovimientosEnum.GASTOS,
                        mesInicio,
                        mesFin)
                .isEmpty();

        if (yaPagado) {
            throw new Exception("El pago de sueldos ya fue registrado para este mes.");
        }

        MovimientoFinancieroEntity entity = new MovimientoFinancieroEntity();
        entity.setTipoMovimiento(MovimientosEnum.GASTOS);
        entity.setConcepto("Pago de sueldos");
        entity.setImporte(importeTotal);
        entity.setFechaRegistro(ahora);
        entity.setCategoria(categoria);
        entity.setAdmin(admin);

        movimientoFinancieroRepository.save(entity);

        ResponceDTO response = new ResponceDTO();
        response.setNumOfErrors(0);
        response.setMensage("Pago de sueldos registrado correctamente.");
        response.setTotal(importeTotal);

        return response;
    }
}
