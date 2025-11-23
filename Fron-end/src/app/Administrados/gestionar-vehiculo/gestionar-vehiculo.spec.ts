import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarVehiculo } from './gestionar-vehiculo';

describe('GestionarVehiculo', () => {
  let component: GestionarVehiculo;
  let fixture: ComponentFixture<GestionarVehiculo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarVehiculo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarVehiculo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
