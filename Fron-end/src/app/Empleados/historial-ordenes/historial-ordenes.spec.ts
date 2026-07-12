import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialOrdenes } from './historial-ordenes';

describe('HistorialOrdenes', () => {
  let component: HistorialOrdenes;
  let fixture: ComponentFixture<HistorialOrdenes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistorialOrdenes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistorialOrdenes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
