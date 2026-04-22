import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialVehiculo } from './historial-vehiculo';

describe('HistorialVehiculo', () => {
  let component: HistorialVehiculo;
  let fixture: ComponentFixture<HistorialVehiculo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistorialVehiculo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistorialVehiculo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
