import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiciosOfresidos } from './servicios-ofresidos';

describe('ServiciosOfresidos', () => {
  let component: ServiciosOfresidos;
  let fixture: ComponentFixture<ServiciosOfresidos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServiciosOfresidos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiciosOfresidos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
