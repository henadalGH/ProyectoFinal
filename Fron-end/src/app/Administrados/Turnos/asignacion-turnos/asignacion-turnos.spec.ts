import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsignacionTurnos } from './asignacion-turnos';

describe('AsignacionTurnos', () => {
  let component: AsignacionTurnos;
  let fixture: ComponentFixture<AsignacionTurnos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AsignacionTurnos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AsignacionTurnos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
