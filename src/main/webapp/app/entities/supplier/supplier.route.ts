import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Supplier } from 'app/shared/model/supplier.model';
import { SupplierService } from './supplier.service';
import { SupplierComponent } from './supplier.component';
import { SupplierDetailComponent } from './supplier-detail.component';
import { SupplierUpdateComponent } from './supplier-update.component';
import { SupplierDeletePopupComponent } from './supplier-delete-dialog.component';
import { ISupplier } from 'app/shared/model/supplier.model';

@Injectable({ providedIn: 'root' })
export class SupplierResolve implements Resolve<ISupplier> {
    constructor(private service: SupplierService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((supplier: HttpResponse<Supplier>) => supplier.body));
        }
        return of(new Supplier());
    }
}

export const supplierRoute: Routes = [
    {
        path: 'supplier',
        component: SupplierComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'supplier/:id/view',
        component: SupplierDetailComponent,
        resolve: {
            supplier: SupplierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'supplier/new',
        component: SupplierUpdateComponent,
        resolve: {
            supplier: SupplierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'supplier/:id/edit',
        component: SupplierUpdateComponent,
        resolve: {
            supplier: SupplierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplierPopupRoute: Routes = [
    {
        path: 'supplier/:id/delete',
        component: SupplierDeletePopupComponent,
        resolve: {
            supplier: SupplierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
