import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PruebaGrafico } from './prueba-grafico';

describe('PruebaGrafico', () => {
  let component: PruebaGrafico;
  let fixture: ComponentFixture<PruebaGrafico>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PruebaGrafico]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PruebaGrafico);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
