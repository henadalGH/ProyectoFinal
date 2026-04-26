package com.example.wmotorproBack.wmotorBack.Modelo.Enums;

public enum EstadoTurnoEnums {
    PENDIENTE,        // creado pero no confirmado
    CONFIRMADO,      // confirmado por el cliente o el local
    EN_PROCESO,      // el turno ya empezó (cliente en atención)
    FINALIZADO,      // servicio completado
    CANCELADO,       // cancelado por cliente o por el local
    AUSENTE,
    PENDIENTE_ASIGNACION
}
