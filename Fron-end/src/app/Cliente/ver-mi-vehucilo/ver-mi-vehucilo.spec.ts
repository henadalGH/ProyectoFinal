import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerMiVehucilo } from './ver-mi-vehucilo';

describe('VerMiVehucilo', () => {
  let component: VerMiVehucilo;
  let fixture: ComponentFixture<VerMiVehucilo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerMiVehucilo]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerMiVehucilo);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
