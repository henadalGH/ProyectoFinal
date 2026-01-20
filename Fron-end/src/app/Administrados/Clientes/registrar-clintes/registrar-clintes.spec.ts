import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarClintes } from './registrar-clintes';

describe('RegistrarClintes', () => {
  let component: RegistrarClintes;
  let fixture: ComponentFixture<RegistrarClintes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrarClintes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrarClintes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
