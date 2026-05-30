import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeOrdenes } from './home-ordenes';

describe('HomeOrdenes', () => {
  let component: HomeOrdenes;
  let fixture: ComponentFixture<HomeOrdenes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeOrdenes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeOrdenes);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
