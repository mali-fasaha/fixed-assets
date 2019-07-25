/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { CapitalWorkInProgressUpdateComponent } from 'app/entities/capital-work-in-progress/capital-work-in-progress-update.component';
import { CapitalWorkInProgressService } from 'app/entities/capital-work-in-progress/capital-work-in-progress.service';
import { CapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';

describe('Component Tests', () => {
  describe('CapitalWorkInProgress Management Update Component', () => {
    let comp: CapitalWorkInProgressUpdateComponent;
    let fixture: ComponentFixture<CapitalWorkInProgressUpdateComponent>;
    let service: CapitalWorkInProgressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [CapitalWorkInProgressUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CapitalWorkInProgressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CapitalWorkInProgressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CapitalWorkInProgressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CapitalWorkInProgress(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CapitalWorkInProgress();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
