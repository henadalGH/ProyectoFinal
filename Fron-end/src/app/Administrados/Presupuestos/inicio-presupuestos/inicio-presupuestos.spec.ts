import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InicioPresupuestos } from './inicio-presupuestos';

describe('InicioPresupuestos', () => {
  let component: InicioPresupuestos;
  let fixture: ComponentFixture<InicioPresupuestos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InicioPresupuestos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InicioPresupuestos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
