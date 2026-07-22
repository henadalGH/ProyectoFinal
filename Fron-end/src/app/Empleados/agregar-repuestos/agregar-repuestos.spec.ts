import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarRepuestos } from './agregar-repuestos';

describe('AgregarRepuestos', () => {
  let component: AgregarRepuestos;
  let fixture: ComponentFixture<AgregarRepuestos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgregarRepuestos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgregarRepuestos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
