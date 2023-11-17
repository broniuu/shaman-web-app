import { TestBed } from '@angular/core/testing';

import { NavbarComunicationService } from './navbar-comunication.service';

describe('NavbarComunicationService', () => {
  let service: NavbarComunicationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NavbarComunicationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
