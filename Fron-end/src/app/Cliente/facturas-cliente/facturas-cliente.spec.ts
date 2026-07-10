import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FacturasCliente } from './facturas-cliente';

describe('FacturasCliente', () => {
  let component: FacturasCliente;
  let fixture: ComponentFixture<FacturasCliente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FacturasCliente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FacturasCliente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
