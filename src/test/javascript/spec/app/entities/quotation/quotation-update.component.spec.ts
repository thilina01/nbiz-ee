/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { QuotationUpdateComponent } from 'app/entities/quotation/quotation-update.component';
import { QuotationService } from 'app/entities/quotation/quotation.service';
import { Quotation } from 'app/shared/model/quotation.model';

describe('Component Tests', () => {
    describe('Quotation Management Update Component', () => {
        let comp: QuotationUpdateComponent;
        let fixture: ComponentFixture<QuotationUpdateComponent>;
        let service: QuotationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [QuotationUpdateComponent]
            })
                .overrideTemplate(QuotationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuotationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuotationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Quotation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.quotation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Quotation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.quotation = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
