import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderEmpleado } from './header-empleado';

describe('HeaderEmpleado', () => {
  let component: HeaderEmpleado;
  let fixture: ComponentFixture<HeaderEmpleado>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderEmpleado]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderEmpleado);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
