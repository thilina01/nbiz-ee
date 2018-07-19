/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SalesPersonUpdateComponent } from 'app/entities/sales-person/sales-person-update.component';
import { SalesPersonService } from 'app/entities/sales-person/sales-person.service';
import { SalesPerson } from 'app/shared/model/sales-person.model';

describe('Component Tests', () => {
    describe('SalesPerson Management Update Component', () => {
        let comp: SalesPersonUpdateComponent;
        let fixture: ComponentFixture<SalesPersonUpdateComponent>;
        let service: SalesPersonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SalesPersonUpdateComponent]
            })
                .overrideTemplate(SalesPersonUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SalesPersonUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesPersonService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SalesPerson(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.salesPerson = entity;
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
                    const entity = new SalesPerson();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.salesPerson = entity;
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
