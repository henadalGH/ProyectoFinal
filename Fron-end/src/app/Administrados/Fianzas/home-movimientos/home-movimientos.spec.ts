import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeMovimientos } from './home-movimientos';

describe('HomeMovimientos', () => {
  let component: HomeMovimientos;
  let fixture: ComponentFixture<HomeMovimientos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeMovimientos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeMovimientos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
