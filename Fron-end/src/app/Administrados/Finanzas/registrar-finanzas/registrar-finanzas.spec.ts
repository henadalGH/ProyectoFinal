import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarFinanzas } from './registrar-finanzas';

describe('RegistrarFinanzas', () => {
  let component: RegistrarFinanzas;
  let fixture: ComponentFixture<RegistrarFinanzas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarFinanzas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrarFinanzas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
