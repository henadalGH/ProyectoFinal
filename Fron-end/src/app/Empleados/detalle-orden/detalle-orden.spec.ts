import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleOrden } from './detalle-orden';

describe('DetalleOrden', () => {
  let component: DetalleOrden;
  let fixture: ComponentFixture<DetalleOrden>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleOrden]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleOrden);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
