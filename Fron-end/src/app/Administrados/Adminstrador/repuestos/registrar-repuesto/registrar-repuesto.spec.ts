import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarRepuesto } from './registrar-repuesto';

describe('RegistrarRepuesto', () => {
  let component: RegistrarRepuesto;
  let fixture: ComponentFixture<RegistrarRepuesto>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarRepuesto]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrarRepuesto);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
