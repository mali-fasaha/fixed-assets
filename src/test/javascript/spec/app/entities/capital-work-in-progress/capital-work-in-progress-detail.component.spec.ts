/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { CapitalWorkInProgressDetailComponent } from 'app/entities/capital-work-in-progress/capital-work-in-progress-detail.component';
import { CapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';

describe('Component Tests', () => {
  describe('CapitalWorkInProgress Management Detail Component', () => {
    let comp: CapitalWorkInProgressDetailComponent;
    let fixture: ComponentFixture<CapitalWorkInProgressDetailComponent>;
    const route = ({ data: of({ capitalWorkInProgress: new CapitalWorkInProgress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [CapitalWorkInProgressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CapitalWorkInProgressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CapitalWorkInProgressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.capitalWorkInProgress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
