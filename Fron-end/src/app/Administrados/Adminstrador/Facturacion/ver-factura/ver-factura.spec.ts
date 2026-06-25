import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerFactura } from './ver-factura';

describe('VerFactura', () => {
  let component: VerFactura;
  let fixture: ComponentFixture<VerFactura>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerFactura]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerFactura);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
