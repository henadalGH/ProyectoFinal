import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PruevaTabla } from './prueva-tabla';

describe('PruevaTabla', () => {
  let component: PruevaTabla;
  let fixture: ComponentFixture<PruevaTabla>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PruevaTabla]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PruevaTabla);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
