import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParaFacturar } from './para-facturar';

describe('ParaFacturar', () => {
  let component: ParaFacturar;
  let fixture: ComponentFixture<ParaFacturar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParaFacturar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParaFacturar);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
