import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistarVehiculo } from './registar-vehiculo';

describe('RegistarVehiculo', () => {
  let component: RegistarVehiculo;
  let fixture: ComponentFixture<RegistarVehiculo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistarVehiculo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistarVehiculo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
