import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovimientosFinancieros } from './movimientos-financieros';

describe('MovimientosFinancieros', () => {
  let component: MovimientosFinancieros;
  let fixture: ComponentFixture<MovimientosFinancieros>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovimientosFinancieros]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovimientosFinancieros);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
