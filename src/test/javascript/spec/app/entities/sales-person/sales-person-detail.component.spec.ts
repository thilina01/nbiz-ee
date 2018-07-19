/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SalesPersonDetailComponent } from 'app/entities/sales-person/sales-person-detail.component';
import { SalesPerson } from 'app/shared/model/sales-person.model';

describe('Component Tests', () => {
    describe('SalesPerson Management Detail Component', () => {
        let comp: SalesPersonDetailComponent;
        let fixture: ComponentFixture<SalesPersonDetailComponent>;
        const route = ({ data: of({ salesPerson: new SalesPerson(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SalesPersonDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SalesPersonDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalesPersonDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.salesPerson).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
