import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-prueva-tabla',
  imports: [FormsModule],
  templateUrl: './prueva-tabla.html',
  styleUrl: './prueva-tabla.css'
})
export class PruevaTabla implements OnInit {

  filas: any[] = [];

  private contadorId = 1;

  ngOnInit(): void {
    this.agregarFila();
  }

  agregarFila(): void {

    this.filas.push({
      id: this.contadorId++,
      cantidad: 0,
      codigo: '',
      trabajo: ''
    });

  }

  eliminarFila(id: number): void {

    this.filas = this.filas.filter(
      fila => fila.id !== id
    );

  }

}