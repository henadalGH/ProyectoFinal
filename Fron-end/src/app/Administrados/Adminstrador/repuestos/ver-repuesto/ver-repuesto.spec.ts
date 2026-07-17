import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerRepuesto } from './ver-repuesto';

describe('VerRepuesto', () => {
  let component: VerRepuesto;
  let fixture: ComponentFixture<VerRepuesto>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerRepuesto]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerRepuesto);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
