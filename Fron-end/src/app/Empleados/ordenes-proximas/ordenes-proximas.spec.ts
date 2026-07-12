import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdenesProximas } from './ordenes-proximas';

describe('OrdenesProximas', () => {
  let component: OrdenesProximas;
  let fixture: ComponentFixture<OrdenesProximas>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdenesProximas]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdenesProximas);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
