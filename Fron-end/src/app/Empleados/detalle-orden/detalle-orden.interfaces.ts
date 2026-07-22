export type TipoItem = 'REPUESTO' | 'MANO_OBRA' | 'SERVICIO' | '';

export interface RepuestoBase {
  id: number;
  codigo: string;
  nombre: string;
  precio?: number;
  marca?: string;
}

export interface RepuestoOrden extends RepuestoBase {
  cantidad: number;
  subtotal?: number;
}

export interface RepuestoModalItem extends RepuestoBase {
  cantidadSeleccionada: number;
  selected: boolean;
}

export interface LineaOrdenDetalle {
  id: number;
  cantidad: number;
  codigo: string;
  trabajoRealizado: string;
  tipoItem: TipoItem;
  selectedRepuestos: RepuestoOrden[];
}
