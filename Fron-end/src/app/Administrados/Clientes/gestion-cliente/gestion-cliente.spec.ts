import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionCliente } from './gestion-cliente';

describe('GestionCliente', () => {
  let component: GestionCliente;
  let fixture: ComponentFixture<GestionCliente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionCliente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionCliente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
