import {
  Directive,
  ElementRef,
  HostListener
} from '@angular/core';

@Directive({
  selector: '[appFormatoMoneda]'
})
export class FormatoMonedaDirective {

  constructor(private el: ElementRef<HTMLInputElement>) {}

  @HostListener('input')
  onInput(): void {

    let valor = this.el.nativeElement.value;

    // Solo números y coma
    valor = valor.replace(/[^\d,]/g, '');

    // Una sola coma
    const partes = valor.split(',');
    if (partes.length > 2) {
      valor = partes[0] + ',' + partes.slice(1).join('');
    }

    let [entero, decimal] = valor.split(',');

    // Puntos de miles
    entero = entero.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

    if (decimal != null) {
      valor = entero + ',' + decimal.substring(0, 2);
    } else {
      valor = entero;
    }

    this.el.nativeElement.value = valor;
  }

}