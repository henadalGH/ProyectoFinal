import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TurnoClienteCasual } from './turno-cliente-casual';

describe('TurnoClienteCasual', () => {
  let component: TurnoClienteCasual;
  let fixture: ComponentFixture<TurnoClienteCasual>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TurnoClienteCasual]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TurnoClienteCasual);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
