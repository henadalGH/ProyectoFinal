import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InicioOrdenes } from './inicio-ordenes';

describe('InicioOrdenes', () => {
  let component: InicioOrdenes;
  let fixture: ComponentFixture<InicioOrdenes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InicioOrdenes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InicioOrdenes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
