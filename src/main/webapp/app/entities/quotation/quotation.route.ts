import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Quotation } from 'app/shared/model/quotation.model';
import { QuotationService } from './quotation.service';
import { QuotationComponent } from './quotation.component';
import { QuotationDetailComponent } from './quotation-detail.component';
import { QuotationUpdateComponent } from './quotation-update.component';
import { QuotationDeletePopupComponent } from './quotation-delete-dialog.component';
import { IQuotation } from 'app/shared/model/quotation.model';

@Injectable({ providedIn: 'root' })
export class QuotationResolve implements Resolve<IQuotation> {
    constructor(private service: QuotationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((quotation: HttpResponse<Quotation>) => quotation.body));
        }
        return of(new Quotation());
    }
}

export const quotationRoute: Routes = [
    {
        path: 'quotation',
        component: QuotationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation/:id/view',
        component: QuotationDetailComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation/new',
        component: QuotationUpdateComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'quotation/:id/edit',
        component: QuotationUpdateComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotationPopupRoute: Routes = [
    {
        path: 'quotation/:id/delete',
        component: QuotationDeletePopupComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
