import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarServicio } from './agregar-servicio';

describe('AgregarServicio', () => {
  let component: AgregarServicio;
  let fixture: ComponentFixture<AgregarServicio>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgregarServicio]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgregarServicio);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
