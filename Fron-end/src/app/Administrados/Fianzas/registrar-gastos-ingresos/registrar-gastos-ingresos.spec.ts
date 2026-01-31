import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarGastosIngresos } from './registrar-gastos-ingresos';

describe('RegistrarGastosIngresos', () => {
  let component: RegistrarGastosIngresos;
  let fixture: ComponentFixture<RegistrarGastosIngresos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarGastosIngresos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrarGastosIngresos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
